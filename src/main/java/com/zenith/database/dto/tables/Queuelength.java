/*
 * This file is generated by jOOQ.
 */
package com.zenith.database.dto.tables;


import com.zenith.database.dto.Public;
import com.zenith.database.dto.tables.records.QueuelengthRecord;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.time.OffsetDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Queuelength extends TableImpl<QueuelengthRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.queuelength</code>
     */
    public static final Queuelength QUEUELENGTH = new Queuelength();
    /**
     * The column <code>public.queuelength.prio</code>.
     */
    public final TableField<QueuelengthRecord, Short> PRIO = createField(DSL.name("prio"), SQLDataType.SMALLINT, this, "");

    /**
     * The column <code>public.queuelength.time</code>.
     */
    public final TableField<QueuelengthRecord, OffsetDateTime> TIME = createField(DSL.name("time"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "");
    /**
     * The column <code>public.queuelength.regular</code>.
     */
    public final TableField<QueuelengthRecord, Short> REGULAR = createField(DSL.name("regular"), SQLDataType.SMALLINT, this, "");

    /**
     * The class holding records for this type
     */
    @Override
    public Class<QueuelengthRecord> getRecordType() {
        return QueuelengthRecord.class;
    }

    private Queuelength(Name alias, Table<QueuelengthRecord> aliased) {
        this(alias, aliased, null);
    }

    private Queuelength(Name alias, Table<QueuelengthRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.queuelength</code> table reference
     */
    public Queuelength(String alias) {
        this(DSL.name(alias), QUEUELENGTH);
    }

    /**
     * Create an aliased <code>public.queuelength</code> table reference
     */
    public Queuelength(Name alias) {
        this(alias, QUEUELENGTH);
    }

    /**
     * Create a <code>public.queuelength</code> table reference
     */
    public Queuelength() {
        this(DSL.name("queuelength"), null);
    }

    public <O extends Record> Queuelength(Table<O> child, ForeignKey<O, QueuelengthRecord> key) {
        super(child, key, QUEUELENGTH);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Queuelength as(String alias) {
        return new Queuelength(DSL.name(alias), this);
    }

    @Override
    public Queuelength as(Name alias) {
        return new Queuelength(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Queuelength rename(String name) {
        return new Queuelength(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Queuelength rename(Name name) {
        return new Queuelength(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<OffsetDateTime, Short, Short> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}