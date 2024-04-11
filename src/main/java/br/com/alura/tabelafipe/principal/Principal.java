package br.com.alura.tabelafipe.principal;

import br.com.alura.tabelafipe.model.DadosModelos;
import br.com.alura.tabelafipe.model.DadosVeiculos;
import br.com.alura.tabelafipe.service.GetMarcasService;
import br.com.alura.tabelafipe.model.Dado;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Scanner;

public class Principal {

    private static final Scanner scan = new Scanner(System.in);
    private static final GetMarcasService service = new GetMarcasService();
    private static final ObjectMapper mapper = new ObjectMapper();

    private String urlBase = "https://parallelum.com.br/fipe/api/v1";
    private String endereco;



    public void exibeMenu() throws JsonProcessingException {



        String menu = """

                Escolha qual tipo de veiculo deseja buscar

                Carros
                Motos
                Caminhoes
                """;

        System.out.println(menu);
        System.out.print("Tipo de veiculo: ");

        String tipoVeiculo = scan.next().toLowerCase();
        scan.nextLine();


        switch (tipoVeiculo) {
            case "carros":
                endereco = urlBase.concat("/carros/marcas");
                break;
            case "motos":
                endereco = urlBase.concat("/motos/marcas");
                break;
            case "caminhoes":
                endereco = urlBase.concat("/caminhoes/marcas");
                break;
            default:
                throw new RuntimeException("Opção inválida");
        }

        System.out.println(endereco);
        var json = service.getMarcas(endereco);


        List<Dado> dadosMarcas = mapper.readValue(json, new TypeReference<List<Dado>>(){});
        dadosMarcas.forEach(System.out::println);

        System.out.print("Digite o código da marca que deja buscar os modelos: ");
        String codigoMarca = scan.next();
        scan.nextLine();

        endereco = endereco.concat("/%s/modelos".formatted(codigoMarca));

        System.out.println(endereco);
        json = service.getMarcas(endereco);

        DadosModelos dadosModelos = mapper.readValue(json, DadosModelos.class);
        dadosModelos.modelos().forEach(System.out::println);

        System.out.print("Digite uma parte do nome do veiculo para filtrar: ");
        String parteNome = scan.next();
        scan.nextLine();

        dadosModelos.modelos().stream().filter(dado -> dado.descricao().toUpperCase().contains(parteNome.toUpperCase())).forEach(System.out::println);

        System.out.print("Digite o código para buscar os valores: ");
        String codigoModelo = scan.next();
        scan.nextLine();

        endereco = endereco.concat("/%s/anos".formatted(codigoModelo));

        System.out.println(endereco);
        json = service.getMarcas(endereco);

        List<Dado> anosList = mapper.readValue(json, new TypeReference<List<Dado>>(){});
        anosList.forEach(System.out::println);
        anosList.forEach(ano -> {
            try {
                var preco = mapper.readValue(service.getMarcas(endereco.concat("/%s".formatted(ano.codigo()))), DadosVeiculos.class);
                System.out.println(preco);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
