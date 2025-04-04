package marc.app.model;

import marc.func.orm.table.annotation.Id;
import marc.func.orm.table.annotation.Table;
import marc.func.orm.table.annotation.relation.OneToOne;

@Table
public class Test4
{

    @Id
    private Integer id;

    @OneToOne
    private Test3 test3;
}
