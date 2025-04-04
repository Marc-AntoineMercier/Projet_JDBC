package marc.app.model;

import marc.framework.table.annotation.Id;
import marc.framework.table.annotation.Table;
import marc.framework.table.annotation.relation.OneToOne;

@Table
public class Test3
{
    @Id
    private Integer id;

    @OneToOne
    private Test2 test2;
}
