package br.com.alura.tabelafipe.principal;

import br.com.alura.tabelafipe.model.Dado;
import br.com.alura.tabelafipe.model.Modelos;
import br.com.alura.tabelafipe.model.Veiculo;
import br.com.alura.tabelafipe.service.ConsomeAPI;
import br.com.alura.tabelafipe.service.ConverteDados;

import java.util.List;
import java.util.Scanner;

public class Principal {

    private static final Scanner scan = new Scanner(System.in);
    private static final ConsomeAPI service = new ConsomeAPI();
    private static final ConverteDados conversor = new ConverteDados();

    private static final String URL_BASE = "https://parallelum.com.br/fipe/api/v1";
    private String endereco;

    public void exibeMenu() {

        String menu = """

                Escolha qual tipo de veiculo deseja buscar pelo código:

                1 - Carros
                2 - Motos
                3 - Caminhoes
                """;

        System.out.println(menu);
        System.out.print("Tipo de veiculo: ");
        var tipoVeiculo = scan.nextInt();
        scan.nextLine();

        switch (tipoVeiculo) {
            case 1:
                endereco = URL_BASE.concat("/carros/marcas");
                break;
            case 2:
                endereco = URL_BASE.concat("/motos/marcas");
                break;
            case 3:
                endereco = URL_BASE.concat("/caminhoes/marcas");
                break;
            default:
                throw new RuntimeException("Opção inválida - Encerrando");
        }

        System.out.println(endereco);

        var json = service.chamaAPI(endereco);

        List<Dado> marcas = conversor.obterListaDaados(json, Dado.class);
        marcas.forEach(System.out::println);

        System.out.print("Digite o código da marca que deja buscar os modelos: ");
        var codigoMarca = scan.next();
        scan.nextLine();

        endereco = endereco.concat("/%s/modelos".formatted(codigoMarca));

        System.out.println(endereco);

        json = service.chamaAPI(endereco);

        Modelos modelos = conversor.obterDados(json, Modelos.class);
        modelos.modelos().forEach(System.out::println);

        System.out.print("Digite uma parte do nome do veiculo para filtrar: ");
        String parteNome = scan.next();
        scan.nextLine();

        modelos.modelos().stream()
                .filter(dado -> dado.descricao().toUpperCase().contains(parteNome.toUpperCase()))
                .forEach(System.out::println);

        System.out.print("Digite o código para buscar os valores: ");
        String codigoModelo = scan.next();
        scan.nextLine();

        endereco = endereco.concat("/%s/anos".formatted(codigoModelo));

        System.out.println(endereco);

        json = service.chamaAPI(endereco);

        List<Dado> anos = conversor.obterListaDaados(json, Dado.class);
        anos.forEach(System.out::println);
        anos.forEach(ano -> {
            var veiculo = conversor.obterDados(service.chamaAPI(endereco.concat("/%s".formatted(ano.codigo()))), Veiculo.class);
            System.out.println(veiculo);
        });
    }
}
