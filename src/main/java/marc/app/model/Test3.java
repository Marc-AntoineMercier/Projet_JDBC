package marc.app.model;

import marc.orm.table.annotation.Id;
import marc.orm.table.annotation.Table;
import marc.orm.table.annotation.relation.OneToOne;

@Table
public class Test3
{
    @Id
    private Integer id;

    @OneToOne
    private Test2 test2;
}
