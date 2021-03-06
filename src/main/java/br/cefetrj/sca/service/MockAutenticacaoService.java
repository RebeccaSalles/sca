package br.cefetrj.sca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.cefetrj.sca.dominio.Aluno;
import br.cefetrj.sca.dominio.Professor;
import br.cefetrj.sca.dominio.repositories.AlunoRepositorio;
import br.cefetrj.sca.dominio.repositories.ProfessorRepositorio;

@Component("mockAuth")
public class MockAutenticacaoService implements AutenticacaoService {

	@Autowired
	private AlunoRepositorio alunoRepo;

	@Autowired
	private ProfessorRepositorio professorRepo;

	@Override
	public void autentica(String login, String senha) {

		Professor professor = professorRepo.findProfessorByMatricula(login);
		Aluno aluno = alunoRepo.findAlunoByMatricula(login);

		if (aluno == null && professor == null) {
			String error = "Seu usuário não está registrado. "
					+ "Entre em contato com o administrador do sistema.";
			throw new IllegalArgumentException(error);
		} else if (aluno != null && professor != null) {
			String error = "Seu usuário não pode ser unicamente identificado. "
					+ "Entre em contato com o administrador do sistema.";
			throw new IllegalArgumentException(error);
		}
	}

	@Override
	public boolean autenticaUsuario(String login, String senha) {
		return isAluno(login) || isProfessor(login);
	}

	public boolean isProfessor(String login) {
		Professor professor = professorRepo.findProfessorByMatricula(login);
		if (professor != null) {
			return true;
		}
		return false;
	}

	public boolean isAluno(String login) {
		Aluno aluno = alunoRepo.findAlunoByMatricula(login);
		if (aluno != null) {
			return true;
		}
		return false;
	}

}
