SELECT schema_name AS DB,
       substring_index(digest_text, ' ', 1) AS type,
       sys.format_time(SUM(sum_timer_wait)) AS latency
FROM performance_schema.events_statements_summary_by_digest
WHERE schema_name='videoserverdb'
GROUP BY schema_name, substring_index(digest_text, ' ', 1)
ORDER BY schema_name, SUM(sum_timer_wait) DESC;  

 

