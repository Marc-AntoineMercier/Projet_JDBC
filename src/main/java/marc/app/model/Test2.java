package marc.app.model;

import marc.framework.table.annotation.Column;
import marc.framework.table.annotation.Id;
import marc.framework.table.annotation.Table;
import marc.framework.table.annotation.relation.OneToOne;

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
