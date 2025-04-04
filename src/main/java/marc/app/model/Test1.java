package marc.app.model;

import marc.func.table.annotation.Column;
import marc.func.table.annotation.Id;
import marc.func.table.annotation.Table;
import marc.func.table.annotation.TableName;

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
