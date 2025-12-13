package com.demo.agent_ai.ai.tools;

import dev.langchain4j.agent.tool.Tool;

public class ModelTools {

    @Tool("Zwraca status zamówienia")
    public String checkOrderStatus(String orderId) {
        return switch (orderId) {
            case "1001" -> "Zamówienie 1001: wysłane";
            case "1002" -> "Zamówienie 1002: oczekuje na płatność";
            default -> "Nie znaleziono zamówienia " + orderId;
        };
    }

    @Tool("Rekomenduje produkt na podstawie preferencji")
    public String recommendProduct(String pref) {
        return "Rekomendacja na podstawie '" + pref + "': Produkt-X";
    }
}
