package marc.app.model;

import marc.func.table.annotation.Column;
import marc.func.table.annotation.Id;
import marc.func.table.annotation.Table;
import marc.func.table.annotation.relation.OneToOne;

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
