package br.com.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.model.Funcionario;

public class LockOtimista {
	private static final Integer CALL_LIGHTMAN_ID = 1;

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Funcionarios-PU");

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Funcionario funcionario = entityManager.find(Funcionario.class, CALL_LIGHTMAN_ID);
		entityManager.close();
//
		TelaDeFuncionarios sessao1 = new TelaDeFuncionarios("SESSAO_1", entityManagerFactory, funcionario, "Call Lightman Moreira");
		sessao1.editarNome();
		sessao1.submeterFormulario();

		TelaDeFuncionarios sessao2 = new TelaDeFuncionarios("SESSAO_2", entityManagerFactory, funcionario, "Call Lightman Silva2");
		sessao2.editarNome();
		sessao2.submeterFormulario();
//
		sessao1.atualizarTelaParaVerificarNome();
		sessao2.atualizarTelaParaVerificarNome();
//
		sessao1.fecharTela();
		sessao2.fecharTela();

		entityManagerFactory.close();
	}

	public static class TelaDeFuncionarios {

		private final String sessao;

		private final EntityManager entityManager;

		private final Funcionario funcionario;

		private final String novoNome;

		public TelaDeFuncionarios(String sessao, EntityManagerFactory entityManagerFactory, Funcionario funcionario,
				String novoNome) {
			this.sessao = sessao;
			this.entityManager = entityManagerFactory.createEntityManager();
			this.funcionario = funcionario;
			this.novoNome = novoNome;
		}

		public void editarNome() {
			funcionario.setNome(novoNome);
		}

		public void submeterFormulario() {
			System.out.println(sessao + ": Iniciando tentativa de atualaizar funcion�rio.");

			try {
				entityManager.getTransaction().begin();
				entityManager.merge(funcionario);
				entityManager.getTransaction().commit();

				System.out.println(sessao + ": Funcion�rio foi atualizado. ");
			} catch (Exception e) {
				System.out.println(sessao + ": Erro na atualiza��o do funcion�rio. MSG: " + e.getMessage());
//				Exception in thread "main" javax.persistence.OptimisticLockException: Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect)
				throw e;
			}
		}

		public void atualizarTelaParaVerificarNome() {
			entityManager.clear();

			Funcionario funcionario = entityManager.find(Funcionario.class, CALL_LIGHTMAN_ID);

			System.out.println(sessao + ": Tela da sess�o " + sessao + " atualizada.");
			if (novoNome.equals(funcionario.getNome())) {
				System.out.println(sessao + ": Bom... Foi atualizado certinho. " + "Agora vou continuar meu trabalho.");
			} else {
				System.out.println(sessao + ": Ueh! N�o tinha deixado o nome " + funcionario.getNome()
						+ " eu tenho certeza que coloquei " + novoNome);
			}
		}

		public void fecharTela() {
			entityManager.close();
		}
	}

}
