package marc.entity;

import lombok.Data;

@Data
public class Passager {

    private Integer id_passager;
    private String nom;
    private String prenom;
    private String email;
    private Integer id_autobus;

}
