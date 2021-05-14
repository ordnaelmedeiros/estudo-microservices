package br.com.ordnaelmedeiros.ems.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import br.com.ordnaelmedeiros.ems.models.CastMember;
import br.com.ordnaelmedeiros.ems.models.CastMember.Type;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@Tag("integration")
class CastMemberRespositoryTest {

	@Inject
	CastMemberRespository respository;
	
	@Test
	@TestTransaction
	void test() {
		CastMember ent = new CastMember();
		ent.name = "Oto";
		ent.type = Type.DIRECTOR;
		
		respository.persist(ent);
		respository.deleteById(ent.id);
		assertTrue(true);
	}
	
	@Test
	void test2() {
		respository.teste();
		assertTrue(true);
	}
	
	@Test
	void test3() {
		boolean esse = new CastMemberRespository().teste();
		assertTrue(esse);
	}

}
