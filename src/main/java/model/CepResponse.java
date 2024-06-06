package model;

import org.json.JSONObject;

public class CepResponse {

    private final String cep;
    private final String logradouro;
    private final String complemento;
    private final String bairro;
    private final String localidade;
    private final String uf;
    private final String ibge;
    private final String gia;
    private final String ddd;
    private final String siafi;

    public CepResponse(JSONObject jsonObject) {
        this.cep = jsonObject.getString("cep");
        this.logradouro = jsonObject.optString("logradouro", "");
        this.complemento = jsonObject.optString("complemento", "");
        this.bairro = jsonObject.optString("bairro", "");
        this.localidade = jsonObject.optString("localidade", "");
        this.uf = jsonObject.optString("uf", "");
        this.ibge = jsonObject.optString("ibge", "");
        this.gia = jsonObject.optString("gia", "");
        this.ddd = jsonObject.optString("ddd", "");
        this.siafi = jsonObject.optString("siafi", "");
    }

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getUf() {
        return uf;
    }

    public String getIbge() {
        return ibge;
    }

    public String getGia() {
        return gia;
    }

    public String getDdd() {
        return ddd;
    }

    public String getSiafi() {
        return siafi;
    }
}
