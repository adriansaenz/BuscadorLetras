package com.example.adrian.buscadorletras;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class Buscador {
    private String url = "";
    private String contenido = "";
    private String tipo = "";

    public Buscador(String url, String contenido, String tipo) {
        this.url = url;
        this.contenido = contenido;
        this.tipo = tipo;
    }

    public String buscarLinks(){
        String aaa = "";
        String texto = "";
        try{
            Document doc = Jsoup.connect(url).userAgent("Mozilla").ignoreHttpErrors(true).timeout(0).get();
            Elements links = doc.select("a[href]");

            for (Element link : links) {
                String l = link.toString();
                if(l.contains(contenido) && l.contains("letras.asp"))
                    aaa = aaa + link.attr("abs:href") + "-/-";
            }
            String ab[] = aaa.split("-/-");
            texto =  new Buscador(ab[0],"letra","texto").buscadorTexto();
        }catch(IOException ex){}
        return texto;
    }

    public String buscadorTexto(){
        String texto = "";
        try{
            Document doc = Jsoup.connect(url).userAgent("Mozilla").ignoreHttpErrors(true).timeout(0).get();
            Elements a = doc.getElementsByTag("p");

            Element letra = a.first();
            String html = letra.toString();
            Document doc2 = Jsoup.parse(html);
            Element link = doc2.select("a").first();

            texto = doc2.body().html();
        }catch(IOException ex){}

        return texto;
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}
