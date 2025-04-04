package marc.app.model;

import marc.func.orm.table.annotation.Id;
import marc.func.orm.table.annotation.Table;
import marc.func.orm.table.annotation.relation.ManyToOne;
import marc.func.orm.table.annotation.relation.OneToOne;

@Table
public class Dodo2
{
    @Id
    private Integer id;

    @ManyToOne
    private Dodo1 dodo1;

}
