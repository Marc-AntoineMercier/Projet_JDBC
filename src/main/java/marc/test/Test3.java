package marc.test;

import marc.database.table.annotation.Id;
import marc.database.table.annotation.Table;
import marc.database.table.annotation.relation.OneToOne;

@Table
public class Test3
{
    @Id
    private Integer id;

    @OneToOne
    private Test2 test2;
}
