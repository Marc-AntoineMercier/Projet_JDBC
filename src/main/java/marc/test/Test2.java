package marc.test;

import marc.database.table.annotation.Column;
import marc.database.table.annotation.Id;
import marc.database.table.annotation.Table;
import marc.database.table.annotation.relation.OneToOne;

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
