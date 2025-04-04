package marc.app.model;

import marc.framework.table.annotation.Column;
import marc.framework.table.annotation.Id;
import marc.framework.table.annotation.Table;
import marc.framework.table.annotation.TableName;

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
