package com.pweb.endereco.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pweb.endereco.dtos.EnderecoPostDTO;
import com.pweb.endereco.dtos.EnderecoPutDTO;
import com.pweb.endereco.models.Endereco;
import com.pweb.endereco.repositories.EnderecoRepository;

@Service
public class EnderecoService {
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Optional<Endereco> buscarEnderecoExistente(Endereco endereco) {
		return enderecoRepository.findByLogradouroAndNumeroAndComplementoAndBairroAndCidadeAndUfAndCepIgnoreCase(
				endereco.getLogradouro(),
				endereco.getNumero(),
				endereco.getComplemento(),
				endereco.getBairro(),
				endereco.getCidade(),
				endereco.getUF(),
				endereco.getCep());
	}
	
	public Endereco atribuirEndereco(EnderecoPostDTO enderecoForm) {
		Endereco endereco = new Endereco(enderecoForm);
		return buscarEnderecoExistente(endereco).orElseGet(() -> {
			Endereco enderecoFinal = enderecoRepository.save(endereco);
			return enderecoFinal;
		});
	}
	
	public Endereco ajustarCampos(EnderecoPutDTO enderecos) {
		Endereco enderecoFinal = new Endereco();
		Endereco enderecoNovo = new Endereco(enderecos.enderecoNovo());
		
		Long idEnderecoAntigo = enderecos.enderecoAtual();
		Endereco enderecoAntigo = enderecoRepository.findById(idEnderecoAntigo).orElse(enderecoNovo);

		// Se os campos obrigatórios forem uma String vazia, manter registro antigo
		enderecoFinal.setLogradouro(enderecoNovo.getLogradouro() == "" ? enderecoAntigo.getLogradouro() : enderecoNovo.getLogradouro());
		enderecoFinal.setBairro(enderecoNovo.getBairro() == "" ? enderecoAntigo.getBairro() : enderecoNovo.getBairro());
		enderecoFinal.setCidade(enderecoNovo.getCidade() == "" ? enderecoAntigo.getCidade() : enderecoNovo.getCidade());
		enderecoFinal.setUF(enderecoNovo.getUF() == "" ? enderecoAntigo.getUF() : enderecoNovo.getUF());
		enderecoFinal.setCep(enderecoNovo.getCep() == "" ? enderecoAntigo.getCep() : enderecoNovo.getCep());

		// Campos opcionais:
		// Se o número for uma String vazia, definir como campo "s/n"
		enderecoFinal.setNumero(enderecoNovo.getNumero() == "" ? "s/n" : enderecoNovo.getNumero());
		// Se o complemento for null ou String vazia, definir como String vazia
		enderecoFinal.setComplemento(enderecoNovo.getComplemento() == null ? "" : enderecoNovo.getComplemento());
		
		return atribuirEndereco(new EnderecoPostDTO(enderecoFinal));
	}
}
