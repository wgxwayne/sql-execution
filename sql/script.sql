create table project_version
(
    project_version_id bigint                             not null comment '项目版本id'
        primary key,
    project_id         bigint                             not null comment '项目id，配置在业务项目',
    project_name       varchar(100)                       not null comment '项目名称',
    project_version    varchar(20)                        not null comment '项目版本',
    gmt_create         datetime default CURRENT_TIMESTAMP null,
    gmt_modified       datetime default CURRENT_TIMESTAMP null,
    constraint projectId_version_unique_index
        unique (project_id, project_version)
)
    charset = utf8;

create table sql_dimension
(
    dimension_id    bigint                             not null comment '维度id'
        primary key,
    execution_id    bigint                             not null comment '执行计划id',
    execution_field varchar(20)                        not null comment 'explain的部分关键字段',
    rows_num        int                                not null comment '第几行',
    value           varchar(100)                       null comment '关键字段的值,可以为空',
    field_score     double                             null comment '关键字段的扣分',
    gmt_create      datetime default CURRENT_TIMESTAMP null,
    gmt_modified    datetime default CURRENT_TIMESTAMP null,
    constraint execution_field_rowsNum_unique_index
        unique (execution_id, execution_field, rows_num)
)
    charset = utf8;

create table sql_execution
(
    execution_id    bigint                             not null comment '执行计划id'
        primary key,
    sql_template_id bigint                             not null comment 'sql模板id',
    sql_content     varchar(1000)                      not null comment '具体sql内容',
    content_score   double                             null comment '具体sql得分',
    sql_time        int                                not null comment 'sql执行耗时-单位：微秒',
    time_score      double                             null comment '耗时扣分',
    gmt_create      datetime default CURRENT_TIMESTAMP null,
    gmt_modified    datetime default CURRENT_TIMESTAMP null,
    constraint content_templateId_unique_index
        unique (sql_template_id, sql_content)
)
    charset = utf8;

create table sql_template
(
    sql_template_id       bigint                             not null comment 'sql模板id'
        primary key,
    project_version_id    bigint                             not null comment '项目版本id',
    sql_template          varchar(1000)                      not null comment 'sql模板内容',
    template_score        double                             null comment 'sql模板得分，取平均分，可能为小数',
    table_name            varchar(100)                       not null comment '数据库表名称',
    database_name         varchar(22)                        not null comment '数据库名称',
    min_score_sql_context varchar(6000)                      null comment '得分最低的sql',
    average_sql_time      double                             null comment '平均得分',
    max_sql_time          int                                null comment '最高耗时',
    min_sql_time          int                                null comment '最低耗时',
    gmt_create            datetime default CURRENT_TIMESTAMP null,
    gmt_modified          datetime default CURRENT_TIMESTAMP null,
    constraint template_versionId_unique_index
        unique (project_version_id, sql_template)
)
    charset = utf8;


