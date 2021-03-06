package br.cefetrj.sca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.cefetrj.sca.dominio.repositories.UsuarioRepositorio;
import br.cefetrj.sca.dominio.usuarios.Usuario;
import br.cefetrj.sca.infra.auth.AutenticadorMoodle;

@Service
@Transactional
public class UsuarioService {
	@Autowired
	private UsuarioRepositorio repositorio;

	@Autowired
	@Qualifier("mockAuth")
	private AutenticacaoService authService;

	@Autowired
	AutenticadorMoodle autenticador;

	public void saveUser(Usuario usr) {
		repositorio.save(usr);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate
	 * update explicitly. Just fetch the entity from db and update it with
	 * proper values within transaction. It will be updated in db once
	 * transaction ends.
	 */
	public void updateUser(Usuario user) {
		Usuario entity = repositorio.findOne(user.getId());
		if (entity != null) {
			entity.setLogin(user.getLogin());
//			entity.setPassword(user.getPassword());
			entity.setNome(user.getNome());
			entity.setEmail(user.getEmail());
			entity.setUserProfiles(user.getUserProfiles());
		}
	}

	public List<Usuario> findAll() {
		return repositorio.findAll();
	}

	public Usuario create(Usuario user) {
		return repositorio.save(user);
	}

	public Usuario findUserById(int id) {
		return repositorio.findOne(id);
	}

	public Usuario update(Usuario user) {
		return repositorio.save(user);
	}

	public void deleteUser(int id) {
		repositorio.delete(id);
	}

	public Usuario findUserByLogin(String login) {
		return repositorio.findUsuarioByLogin(login);
	}

	public boolean isLoginJaExistente(int id, String login) {
		return repositorio.findUsuarioByLogin(login) != null;
	}
}
