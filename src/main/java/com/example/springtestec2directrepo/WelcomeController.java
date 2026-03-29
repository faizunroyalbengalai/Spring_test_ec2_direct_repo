package com.example.springtestec2directrepo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@RestController
public class WelcomeController {

    @Autowired(required = false)
    private DataSource dataSource;

    @GetMapping("/")
    public ResponseEntity<String> welcome() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html lang=\"en\"><head>");
        html.append("<meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">");
        html.append("<title>UDAP Agent</title>");
        html.append("<style>");
        html.append("*{box-sizing:border-box;margin:0;padding:0}"
            + "body{font-family:'Segoe UI',Arial,sans-serif;background:linear-gradient(135deg,#0f0c29,#302b63,#24243e);"
            + "min-height:100vh;display:flex;align-items:center;justify-content:center;color:#fff}"
            + ".card{background:rgba(255,255,255,.08);backdrop-filter:blur(12px);border:1px solid rgba(255,255,255,.15);"
            + "border-radius:20px;padding:48px 56px;text-align:center;max-width:500px;width:90%;box-shadow:0 20px 60px rgba(0,0,0,.4)}"
            + ".logo{font-size:2.4rem;font-weight:700;margin-bottom:6px}"
            + ".logo span{background:linear-gradient(90deg,#6ee7f7,#a78bfa);-webkit-background-clip:text;-webkit-text-fill-color:transparent}"
            + ".by{font-size:.8rem;color:rgba(255,255,255,.45);letter-spacing:2px;text-transform:uppercase;margin-bottom:32px}"
            + ".db{margin-top:20px;padding:14px 18px;border-radius:12px;text-align:left;font-size:.88rem}"
            + ".connected{background:rgba(52,199,89,.15);border:1px solid rgba(52,199,89,.4)}"
            + ".disconnected{background:rgba(255,59,48,.12);border:1px solid rgba(255,59,48,.3)}"
            + ".dot{display:inline-block;width:9px;height:9px;border-radius:50%;margin-right:8px;vertical-align:middle}"
            + ".green{background:#34c759;box-shadow:0 0 6px #34c759}.red{background:#ff3b30;box-shadow:0 0 6px #ff3b30}"
            + ".dbinfo{font-size:.76rem;color:rgba(255,255,255,.5);margin-top:5px;padding-left:17px;word-break:break-all}"
            + ".divider{border:none;border-top:1px solid rgba(255,255,255,.1);margin:24px 0}"
            + ".footer{font-size:.72rem;color:rgba(255,255,255,.25);margin-top:24px}");
        html.append("</style></head><body><div class=\"card\">");
        html.append("<div class=\"logo\">UDAP <span>Agent</span></div>");
        html.append("<div class=\"by\">Developed by Royal Bengal AI</div>");
        html.append("<hr class=\"divider\">");
        if (dataSource != null) {
            try (Connection conn = dataSource.getConnection()) {
                DatabaseMetaData meta = conn.getMetaData();
                String product = meta.getDatabaseProductName();
                String catalog = conn.getCatalog();
                String display = (catalog != null && !catalog.isEmpty()) ? catalog : meta.getURL();
                html.append("<div class=\"db connected\">");
                html.append("<span class=\"dot green\"></span><strong>Database Connected</strong>");
                html.append("<div class=\"dbinfo\">").append(product).append(" &bull; ").append(display).append("</div>");
                html.append("</div>");
            } catch (Exception e) {
                html.append("<div class=\"db disconnected\">");
                html.append("<span class=\"dot red\"></span><strong>Database Unreachable</strong>");
                html.append("<div class=\"dbinfo\">").append(e.getMessage()).append("</div>");
                html.append("</div>");
            }
        }
        html.append("<div class=\"footer\">Powered by Spring Boot</div>");
        html.append("</div></body></html>");
        return ResponseEntity.ok()
            .header("Content-Type", "text/html; charset=UTF-8")
            .body(html.toString());
    }
}
