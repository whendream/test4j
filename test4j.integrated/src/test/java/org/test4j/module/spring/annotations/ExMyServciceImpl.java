package org.test4j.module.spring.annotations;

import java.util.HashMap;

import org.test4j.fortest.reflector.MyServiceImpl;
import org.test4j.module.ICore;
import org.test4j.tools.reflector.MethodAccessor;

@SuppressWarnings("rawtypes")
public class ExMyServciceImpl extends MyServiceImpl implements ExMyService, ICore {
	public String protectedInvoked() {
		return super.protectedInvoked();
	}

	public String privateInvoked(String in) throws Throwable {
		MethodAccessor<String> method = new MethodAccessor<String>(MyServiceImpl.class, "privateInvoked", String.class);
		return method.invoke(this, new Object[] { in });
	}

	public int primitivePara(int i, boolean bl) {
		Object ret = reflector.invoke(MyServiceImpl.class, this, "primitivePara", i, bl);
		return (Integer) ret;
	}

	public int mapPara(HashMap map) {
		Object o = reflector.invoke(MyServiceImpl.class, this, "mapPara", map);
		return (Integer) o;
	}

	public void invokeException() {
		reflector.invoke(MyServiceImpl.class, this, "invokeException");
	}
}
