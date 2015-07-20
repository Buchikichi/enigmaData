package to.kit.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public final class LogInjectorAspect {
	private static final Logger LOG = LogManager.getLogger();

	@Before("execution(* to.kit.enigma..*.*(..))")
	public void before(JoinPoint joinPoint) throws Throwable {
		Signature sig = joinPoint.getSignature();
		LOG.debug("PASSING:" + sig.toLongString());
	}
}
