select variable_value from performance_schema.status_by_user where user='vsuser' and  variable_name in ('bytes_sent','bytes_received');
