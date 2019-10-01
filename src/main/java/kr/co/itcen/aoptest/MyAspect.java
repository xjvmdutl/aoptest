package kr.co.itcen.aoptest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
//어플리케이션 context에서 스캐닝을 안하기 때문에 컴포넌트라고 어노테이션을 걸어주어야 한다.
@Component
@Aspect
public class MyAspect {
	//PointCut 기술 방법1: full포인트컷 기술 방법
	//반환 타입 패키지.클래스이름.메소드명(파라미터타입) throws 예외
	//메소드를 정확하게 기술할 때  
	@Before("execution(ProductVo kr.co.itcen.aoptest.ProductService.find(String))")//이 메소드가 실행되기전에
	public void beforeAdvice() {
		System.out.println("---- Before advice ----");
	}
	//PointCut 기술 방법1: throws구문은 생략이 가능하다.
	//리턴타입은 *로 와일드 카드 사용가능
	//파라미터기술을 (..)으로 축약(파라미터의 갯수,종류 형식,순서 상관없이)
	//오버로드가 되어 있는경우에는 파라미터를 적어주어야 하고 없을경우에는 ..으로 축약가능
	//하나의 클래스의 오버로드가 안된 메소드를 지정할 때
	@After("execution(* kr.co.itcen.aoptest.ProductService.find(..))")
	public void afterAdvice() {
		System.out.println("---- After advice ----");
	}
	//메소드 이름은 *로 모든 메소드 지정
	//하나의 클래스의 모든 메소드를 기술
	@AfterReturning("execution(* kr.co.itcen.aoptest.ProductService.*(..))")
	public void AfterReturningAdvice() {
		System.out.println("---- AfterReturning advice ----");
	}
	//1. 패키지 이름을 (*..*)로 모든 패키지 지정
	//	모든 패키지에서 productservice만
	// 특정 클래스의 모든 메소드를 지정할 때 보통 많이 쓰는 방법
	@AfterThrowing(value="execution(* *..*.ProductService.*(..))",throwing="ex")//예외를 받아야한다
	public void AfterThrowingAdvice(Throwable ex) {
		System.out.println("---- AfterThrowing advice ---- : "+ex);
	}
	@Around("execution(* *..*.ProductService.*(..))")
	public Object AroundAdvice(ProceedingJoinPoint pjp)throws Throwable {
		//before advice
		System.out.println("---- @Around(before) ----");
		
		//1.PointCut이 되는 메소드 호출(파라미터는 그대로 전달)
		//Object result=pjp.proceed();//find(메소드를 말하는 것이다),반드시 선언을 해주어야 한다.
		//2.PointCut이 되는 메소드 호출(파라미터 변경)
		Object[] parameters= {"Camera"};
		Object result=pjp.proceed(parameters);
		//after advice
		System.out.println("---- @Around(after) ----");
		
		return result;
	}
	
}
