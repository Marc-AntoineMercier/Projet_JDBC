package marc.app.model;

import marc.func.orm.table.annotation.Column;
import marc.func.orm.table.annotation.Id;
import marc.func.orm.table.annotation.Table;

@Table
public class Dodo1
{
    @Id
    private Integer id;

    @Column(name = "toto_name")
    private String toto;

    @Column(name = "tata_name")
    private String tata;

}
