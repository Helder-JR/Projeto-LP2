package Entidades;

import static java.lang.Math.floor;

/**
 * Representação de um projeto de lei. Além dos atributos herdados de ProjetoLegislativoAbstract essa classe também
 * possui uma variável referente ao estado conclusivo da sua tramitação.
 */
public class ProjetoLei extends ProjetoLegislativoAbstract {

    /**
     * Indica se o projeto de lei pode ser apreciado apenas nas comissões sem a necessídade de ir ao plenário.
     */
    private boolean conclusivo;

    /**
     * Cria um objeto do tipo Projeto de Lei.
     *
     * @param dni o DNI da pessoa que propôs esse projeto de lei.
     * @param ano o ano em que esse projeto foi criado/proposto.
     * @param ementa a ementa com a descrição do projeto de lei.
     * @param interesses os interesses aos quais o projeto de lei trata.
     * @param url o endereço da internet que contém o documento com a descrição do projeto.
     * @param conclusivo o estado da tramitação do projeto.
     * @param codigo o código que identifica esse projeto de lei.
     * @param codigoGlobal o código que identifica esse projeto de lei dentro do sistema.
     */
    public ProjetoLei(String dni, int ano, String ementa, String interesses, String url, boolean conclusivo, String codigo, int codigoGlobal) {
        super(dni, ano, ementa, interesses, url, codigo, codigoGlobal);
        this.conclusivo = conclusivo;
        this.tipoProjeto = "PL";
    }

    /**
     * A reprensentação textual do projeto de lei. Segue dois formatos diferentes (variando apenas se é conclusivo ou
     * não:
     * "Projeto de Lei - Código - Autor - Ementa - Conclusiva - Situação Atual"
     * "Projeto de Lei - Código - Autor - Ementa - Situação Atual"
     *
     * @return a String que representa o projeto de lei.
     */
    public String toString() {
        String toString;
        if (this.conclusivo) {
           toString = String.format("Projeto de Lei - %s - %s - %s - Conclusiva - %s", this.codigo, this.autor, this.ementa, this.situacaoAtual);
        } else {
            toString = String.format("Projeto de Lei - %s - %s - %s - %s", this.codigo, this.autor, this.ementa, this.situacaoAtual);
        }
        return toString;
    }

    /**
     * Altera a situação atual do projeto de lei, com base no local onde está sendo votado.
     *
     * @param resultado o resultado da votação, sendo true caso a proposta seja aprovada ou false caso contrário.
     * @param proximoLocal o próximo local em que a proposta será votada.
     */
    @Override
    public void setSituacaoAtual(boolean resultado, String proximoLocal) {
        this.tramitacao.remove(this.tramitacao.size()-1);
        setSituacao(resultado, proximoLocal);
    }

    /**
     * Altera a situação atual do projeto de lei.
     *
     * @param resultado o resultado da votação até o momento.
     * @param proximoLocal o próximo local para onde a votação irá seguir.
     */
    private void setSituacao(boolean resultado, String proximoLocal) {
        if (conclusivo) {
            setSituacaoConclusivo(resultado, proximoLocal);
        } else {
            setSituacaoNaoConclusivo(resultado, proximoLocal);
        }
    }

    private void setSituacaoNaoConclusivo(boolean resultado, String proximoLocal) {
        if (!"plenario".equals(this.local)) { // Se o local da proposta nao for o plenario
            if (resultado) {
                this.tramitacao.add(String.format("APROVADO (%s)", this.local));
            } else {
                this.tramitacao.add(String.format("REJEITADO (%s)", this.local));
            }
            this.situacaoAtual = String.format("EM VOTACAO (%s)", capitalize(proximoLocal));
            this.tramitacao.add(situacaoAtual);
            this.local = proximoLocal;
        } else {
            if (resultado) {
                this.situacaoAtual = "APROVADO";
                this.tramitacao.add(String.format("APROVADO (%s)", capitalize(this.local)));
                this.local = "-";
            } else {
                this.situacaoAtual = "ARQUIVADO";
                this.tramitacao.add(String.format("REJEITADO (%s)", capitalize(this.local)));
                this.local = "-";
            }
        }
    }

    private void setSituacaoConclusivo(boolean resultado, String proximoLocal) {
        if (!"-".equals(proximoLocal)) { // Se nao tiver proximo local
            if (resultado) {
                this.situacaoAtual = String.format("EM VOTACAO (%s)", proximoLocal);
                this.tramitacao.add(String.format("APROVADO (%s)", this.local));
                this.tramitacao.add(situacaoAtual);
                this.local = proximoLocal;
            } else {
                this.situacaoAtual = "ARQUIVADO";
                this.tramitacao.add(String.format("REJEITADO (%s)", this.local));
                this.local = "-";
            }
        } else {
            if (resultado) {
                this.situacaoAtual = "APROVADO";
                this.tramitacao.add(String.format("APROVADO (%s)", this.local));
                this.local = "-";
            } else {
                this.situacaoAtual = "ARQUIVADO";
                this.tramitacao.add(String.format("REJEITADO (%s)", this.local));
                this.local = "-";
            }
        }
    }

    /**
     * Verifica se a quantidade de deputados(as) presentes é o necessário para que uma votação seja válida. Para que
     * isso aconteça a quantidade de deputados(as) presentes deve ser maior ou igual a 50% do total, acrescida uma
     * unidade.
     *
     * @param deputadosPresentes a quantidade de deputados(as) presentes atualmente.
     * @param totalDeputados o total de deputados.
     * @return um booleano true caso a quantidade mínima de deputados(as) presentes é o ncecessário pra que a votação
     * seja válida, ou false caso contráiro.
     */
    @Override
    public boolean quorumMinimo(int deputadosPresentes, int totalDeputados) {
        return (deputadosPresentes >= floor(totalDeputados/2)+1);
    }

    /**
     * Verifica se o total de votos que o projeto recebeu é o necessário para que ele seja aprovado. Para que isso
     * acontaça a quantidade total de votos deve ser maior ou igual a 50% da quantidade total de deputados(as),
     * acrescida de uma unidade.
     *
     * @param deputadosPresentes o total de deputados(as) que participaram da votação.
     * @param totalVotos o total de votos que a proposta legislativa obteve.
     * @return um booleano true caso a quantidade mínima de votos seja atingida, ou false caso contrário.
     */
    @Override
    public boolean calculaVotoMinimo(int deputadosPresentes , int totalDeputados, int totalVotos) {
        return (totalVotos >= floor(deputadosPresentes/2.0)+1);
    }
}
