package com.kjun.drools;

import java.util.Iterator;

import org.kie.api.KieBase;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

public class Drools_WAS {
	public static final void main(String[] args) {

		try {

			// create new KnowledgeBuilder
			KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

			// setting "rule file" to KnowledgeBuilder
			kbuilder.add(ResourceFactory.newClassPathResource("number_of_car.drl"), ResourceType.DRL);

			// create new KieBase
			KieBase kiebase = kbuilder.newKnowledgeBase();

			//rule list of rule file 
			Iterator<KiePackage> lKiePackageIterator = kiebase.getKiePackages().iterator();
			while (lKiePackageIterator.hasNext()) {
				KiePackage lKiePackage = lKiePackageIterator.next();
				Iterator<Rule> lRuleIterator = lKiePackage.getRules().iterator();
				while (lRuleIterator.hasNext()) {
					Rule lRule = lRuleIterator.next();
					System.out.println(lRule.getName());
				}
			}

			// 새로운 지식 베이스 세션 생성
			KieSession session = kiebase.newKieSession();

			System.out.println(session.getSessionClock().getCurrentTime());

			// logger등록(팩트에 의해 생성된 엑티베이션(Activation)관련 이벤트만 로깅
			// session.addEventListener(new
			// CustomAgendaEventListener(session.getIdentifier()));

			// CheckingMyCar클래스를 이용한 팩트 추가
			int a = 1;

			session.insert(new CheckingMyCar(a));

			System.out.println(session.getSessionClock().getCurrentTime());

			System.out.println(session.getIdentifier());

			System.out.println("RuleEngineStartTime : " + session.getSessionClock().getCurrentTime() 
					+ "Matched count of Rule : " + session.fireAllRules());

			session.dispose();

			// File aFile = new File("src/main/resources");
			// File[] fileList = aFile.listFiles();
			//
			// for (File tempFile : fileList) {
			// if (tempFile.isFile()) {
			// String tempPath = tempFile.getParent();
			// String tempFileName = tempFile.getName();
			// System.out.println(tempFileName);
			// }
			// }

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
