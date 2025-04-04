package marc.app.model;

import marc.orm.table.annotation.Column;
import marc.orm.table.annotation.Id;
import marc.orm.table.annotation.Table;
import marc.orm.table.annotation.TableName;

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


}
