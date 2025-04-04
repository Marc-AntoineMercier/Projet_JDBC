package marc.test;

import marc.database.table.annotation.Column;
import marc.database.table.annotation.Id;
import marc.database.table.annotation.Table;
import marc.database.table.annotation.TableName;
import marc.database.table.annotation.relation.OneToOne;

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
//
//    @OneToOne
//    private Test2 test2;

}
