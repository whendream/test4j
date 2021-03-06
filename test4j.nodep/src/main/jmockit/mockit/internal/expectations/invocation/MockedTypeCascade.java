/*
 * Copyright (c) 2006-2013 Rogério Liesenfeld
 * This file is subject to the terms of the MIT license (see LICENSE.txt).
 */
package mockit.internal.expectations.invocation;

import java.lang.reflect.*;
import java.util.*;

import mockit.internal.expectations.mocking.*;
import mockit.internal.state.*;
import mockit.internal.util.*;

public final class MockedTypeCascade
{
   private final MockedType mockedType;
   private final Map<String, Class<?>> cascadedTypesAndMocks;

   public MockedTypeCascade(MockedType mockedType)
   {
      this.mockedType = mockedType;
      cascadedTypesAndMocks = new HashMap<String, Class<?>>(4);
   }

   public boolean isSharedBetweenTests() { return mockedType != null && mockedType.fieldFromTestClass; }

   public static Object getMock(
      String mockedTypeDesc, Object mockInstance, String returnTypeDesc, String genericReturnTypeDesc)
   {
      char typeCode = returnTypeDesc.charAt(0);

      if (typeCode != 'L') {
         return null;
      }

      MockedTypeCascade cascade = TestRun.getExecutingTest().getMockedTypeCascade(mockedTypeDesc, mockInstance);

      if (cascade == null) {
         return null;
      }

      String returnTypeInternalName = null;

      if (genericReturnTypeDesc != null) {
         returnTypeInternalName = getGenericReturnType(genericReturnTypeDesc, cascade);
      }

      if (returnTypeInternalName == null) {
         returnTypeInternalName = getReturnTypeIfCascadingSupportedForIt(returnTypeDesc);
      }

      return returnTypeInternalName == null ? null : cascade.getCascadedMock(returnTypeInternalName);
   }

   private static String getGenericReturnType(String genericReturnTypeDesc, MockedTypeCascade cascade)
   {
      if (cascade.mockedType == null) {
         return null;
      }

      String typeName = getInternalTypeName(genericReturnTypeDesc);
      Type mockedType = cascade.mockedType.declaredType;

      if (!(mockedType instanceof ParameterizedType)) {
         mockedType = ((Class<?>) mockedType).getGenericSuperclass();
      }

      if (mockedType instanceof ParameterizedType) {
         ParameterizedType mockedGenericType = (ParameterizedType) mockedType;
         TypeVariable<?>[] typeParameters = ((Class<?>) mockedGenericType.getRawType()).getTypeParameters();
         Type[] actualTypeArguments = mockedGenericType.getActualTypeArguments();

         for (int i = 0; i < typeParameters.length; i++) {
            TypeVariable<?> typeParameter = typeParameters[i];

            if (typeName.equals(typeParameter.getName())) {
               Type actualType = actualTypeArguments[i];
               Class<?> actualClass;

               if (actualType instanceof Class<?>) {
                  actualClass = (Class<?>) actualType;
               }
               else if (actualType instanceof WildcardType) {
                  actualClass = (Class<?>) ((WildcardType) actualType).getUpperBounds()[0];
               }
               else {
                  return null;
               }

               return getReturnTypeIfCascadingSupportedForIt(actualClass);
            }
         }
      }

      return null;
   }

   private static String getInternalTypeName(String typeDesc) { return typeDesc.substring(1, typeDesc.length() - 1); }

   private static String getReturnTypeIfCascadingSupportedForIt(Class<?> returnType)
   {
      String typeName = returnType.getName().replace('.', '/');
      return isTypeSupportedForCascading(typeName) ? typeName : null;
   }

   private static boolean isTypeSupportedForCascading(String typeName)
   {
      return !typeName.startsWith("java/lang/") || typeName.contains("/Process") || typeName.endsWith("/Runnable");
   }

   private static String getReturnTypeIfCascadingSupportedForIt(String typeDesc)
   {
      String typeName = getInternalTypeName(typeDesc);
      return isTypeSupportedForCascading(typeName) ? typeName : null;
   }

   private Object getCascadedMock(String returnTypeInternalName)
   {
      Class<?> returnType = cascadedTypesAndMocks.get(returnTypeInternalName);

      if (returnType == null) {
         returnType = registerIntermediateCascadingType(returnTypeInternalName);
      }

      return createNewCascadedInstanceOrUseNonCascadedOneIfAvailable(returnType);
   }

   private Class<?> registerIntermediateCascadingType(String returnTypeInternalName)
   {
      Class<?> returnType = ClassLoad.loadByInternalName(returnTypeInternalName);

      cascadedTypesAndMocks.put(returnTypeInternalName, returnType);
      TestRun.getExecutingTest().addCascadingType(returnTypeInternalName, null);
      return returnType;
   }

   private Object createNewCascadedInstanceOrUseNonCascadedOneIfAvailable(Class<?> mockedType)
   {
      InstanceFactory instanceFactory = TestRun.mockFixture().findInstanceFactory(mockedType);

      if (instanceFactory == null) {
         CascadingTypeRedefinition typeRedefinition = new CascadingTypeRedefinition(mockedType);
         instanceFactory = typeRedefinition.redefineType();
      }
      else {
         Object lastInstance = instanceFactory.getLastInstance();

         if (lastInstance != null) {
            return lastInstance;
         }
      }

      Object cascadedInstance = instanceFactory.create();
      instanceFactory.clearLastInstance();
      TestRun.getExecutingTest().addInjectableMock(cascadedInstance);
      return cascadedInstance;
   }

   public void discardCascadedMocks()
   {
      cascadedTypesAndMocks.clear();
   }
}
