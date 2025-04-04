package marc.app.model;

import marc.func.orm.table.annotation.Column;
import marc.func.orm.table.annotation.Id;
import marc.func.orm.table.annotation.Table;
import marc.func.orm.table.annotation.relation.OneToOne;

@Table
public class Test2
{
    @Id
    @Column(name = "id_test2")
    private Integer id;
    private String dodo;
    private String list;

    @OneToOne
    private Test1 test1;

}
