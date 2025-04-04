package marc.app.model;

import marc.func.orm.table.annotation.Column;
import marc.func.orm.table.annotation.Id;
import marc.func.orm.table.annotation.Table;
import marc.func.orm.table.annotation.TableName;
import marc.func.orm.table.annotation.relation.OneToOne;

@Table
@TableName(name = "sleep")
public class Test1
{
    @Id
    @Column(name = "id_test1")
    private Long id;

    @Column(name = "first_name", value = 10, defaultValue = "MARC", nullable = true)
    private String fname;
    private String lname;

    @OneToOne
    private Test3 test3;

}
