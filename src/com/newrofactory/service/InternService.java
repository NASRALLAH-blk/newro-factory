package com.newrofactory.service;

import com.newrofactory.model.Intern;
import com.newrofactory.persistence.InternDAO;
import java.util.List;

public class InternService {
    private static InternService instance;
    private final InternDAO dao = InternDAO.getInstance();

    private InternService() {}

    public static synchronized InternService getInstance() {
        if (instance == null) {
            instance = new InternService();
        }
        return instance;
    }

    public List<Intern> getInternsPage(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        return dao.getAll(pageSize, offset);
    }

    public void addIntern(Intern intern) throws Exception {
      
        if (intern.getFirstName().trim().isEmpty() || intern.getLastName().trim().isEmpty()) {
            throw new Exception("Le nom et le prénom ne peuvent pas être vides.");
        }
        // Règle 2 : La date de fin doit être postérieure à la date d'arrivée
        if (intern.getFormationOver() != null && !intern.getFormationOver().isAfter(intern.getArrival())) {
            throw new Exception("La date de fin doit être après la date d'arrivée !");
        }
       
        dao.add(intern);
    }

    public void removeIntern(long id) {
        dao.delete(id);
    }
}