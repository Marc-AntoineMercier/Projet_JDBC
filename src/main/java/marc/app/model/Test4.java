package marc.app.model;

import marc.func.table.annotation.Id;
import marc.func.table.annotation.Table;
import marc.func.table.annotation.relation.OneToOne;

@Table
public class Test4
{

    @Id
    private Integer id;

    @OneToOne
    private Test3 test3;
}
