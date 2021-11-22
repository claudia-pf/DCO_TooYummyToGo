package pt.tooyummytogo.strategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.tooyummytogo.config.Configuration;
import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.exceptions.NaoExistemComerciantesException;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;


public class Pesquisa {

	private List<Comerciante> catC;
	private HashMap<String, CriterioPesquisa> criterios;

	public Pesquisa(List<Comerciante> catC) {
		this.catC = catC;
		criterios = new HashMap<>();
	}

	/**
	 * @param pos localizacao actual do utilizador
	 * @return lista de comerciantes com produtos disponiveis num raio de 5km durante a proxima hora (valores default)
	 * @throws NaoExistemComerciantesException
	 */
	public List<ComercianteInfo> pesquisaDefault(PosicaoCoordenadas pos) throws NaoExistemComerciantesException {
		preencheCriterios("pesquisaDefault");
		criterios.put("raio", new CriterioRaio(pos));
		return getListaComerciantes(criteriosToList());
	}

	public List<ComercianteInfo> redefineRaio(PosicaoCoordenadas pos, double raio) throws NaoExistemComerciantesException {
		criterios.put("raio", new CriterioRaio(pos, raio));
		return getListaComerciantes(criteriosToList());
	}

	public List<ComercianteInfo> redefinePeriodo(LocalDateTime inicio, LocalDateTime fim) throws NaoExistemComerciantesException{
		criterios.put("horario", new CriterioHorario(inicio, fim));
		return getListaComerciantes(criteriosToList());
	}

	private List<CriterioPesquisa> criteriosToList(){
		List<CriterioPesquisa> lista = new ArrayList<>();
		criterios.forEach((key,value) -> lista.add(value));
		return lista;
	}


	private List<ComercianteInfo> getListaComerciantes(List<CriterioPesquisa> criteriosPesquisar) throws NaoExistemComerciantesException{
		List<ComercianteInfo> res = new ArrayList<>();
		for (Comerciante c : catC ) {
			if (correspondeAosCriterios(c, criteriosPesquisar)) {
				res.add(new ComercianteInfo(c.getUsername(), c.getPosicao()));
			}
		}
		if(res.isEmpty()) {
			throw new NaoExistemComerciantesException("Não existem comerciantes disponíveis para os critérios de pesquisa definidos");
		}
		return res;
	}

	private boolean correspondeAosCriterios(Comerciante c, List<CriterioPesquisa> criterios) {
		for(CriterioPesquisa p: criterios) {
			if(!p.corresponde(c)) {
				return false;
			}			
		}
		return true;
	}

	private void preencheCriterios(String criteriosPesquisa) {

		Configuration config = Configuration.getInstance();	
		String criteriosAUsar = config.getProperty(criteriosPesquisa);
		if(criteriosAUsar != null) {
			for (String criterioNome : criteriosAUsar.split(",")) {
				try {
					@SuppressWarnings("unchecked")
					Class<CriterioPesquisa> klass = (Class<CriterioPesquisa>) Class.forName(criterioNome);
					Constructor<CriterioPesquisa> cons = klass.getConstructor();
					criterios.put(criterioNome, cons.newInstance());

				} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
						| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					//do nothing
					// o CriterioRaio cai aqui porque nao tem construtor vazio mas isso mas os metodos que precisarem dele metem-no manualmente
				}
			}			

		}else {
			criterios.put("horario", new CriterioHorario());
		}

	}

}
