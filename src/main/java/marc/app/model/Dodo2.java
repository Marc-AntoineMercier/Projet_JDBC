package marc.app.model;

import marc.orm.table.annotation.Id;
import marc.orm.table.annotation.Table;
import marc.orm.table.annotation.relation.ManyToOne;

@Table
public class Dodo2
{
    @Id
    private Integer id;

    @ManyToOne
    private Dodo1 dodo1;


}
