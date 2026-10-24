package com.newrofactory.ui;

import com.newrofactory.model.Intern;
import com.newrofactory.service.InternService;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private final Scanner scanner = new Scanner(System.in);
    private final InternService service = InternService.getInstance();

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- MENU JACQUES B. FORMATION ---");
            System.out.println("1. Lister les stagiaires (page par page)");
            System.out.println("2. Créer un stagiaire");
            System.out.println("3. Supprimer un stagiaire");
            System.out.println("4. Quitter");
            System.out.print("Votre choix : ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> listInterns();
                case "2" -> addIntern();
                case "3" -> deleteIntern();
                case "4" -> running = false;
                default -> System.out.println("Option non reconnue.");
            }
        }
    }

    private void listInterns() {
        System.out.print("Numéro de la page (ex: 1) : ");
        int page = Integer.parseInt(scanner.nextLine());
        List<Intern> list = service.getInternsPage(page, 5);

        if (list.isEmpty()) {
            System.out.println("Aucun stagiaire trouvé sur cette page.");
        } else {
            list.forEach(System.out::println);
        }
    }

    private void addIntern() {
        try {
            System.out.print("Prénom : ");
            String prenom = scanner.nextLine();

            System.out.print("Nom : ");
            String nom = scanner.nextLine();

            System.out.print("Date d'arrivée (ex: 2026-03-01) : ");
            LocalDate arrivee = LocalDate.parse(scanner.nextLine());

            System.out.print("Date de fin (laisser vide si aucune) : ");
            String finStr = scanner.nextLine();
            LocalDate fin = finStr.trim().isEmpty() ? null : LocalDate.parse(finStr);

            System.out.print("ID Promotion : ");
            long promoId = Long.parseLong(scanner.nextLine());

            Intern intern = new Intern(prenom, nom, arrivee, fin, promoId);
            service.addIntern(intern);
            System.out.println("Stagiaire enregistré avec succès !");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void deleteIntern() {
        System.out.print("ID du stagiaire à supprimer : ");
        long id = Long.parseLong(scanner.nextLine());
        service.removeIntern(id);
        System.out.println("Stagiaire supprimé.");
    }
}