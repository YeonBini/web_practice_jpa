package jpabook.jpashop.domain.n_plus_1;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAcademy is a Querydsl query type for Academy
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAcademy extends EntityPathBase<Academy> {

    private static final long serialVersionUID = -1785643202L;

    public static final QAcademy academy = new QAcademy("academy");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<Subject, QSubject> subjects = this.<Subject, QSubject>createList("subjects", Subject.class, QSubject.class, PathInits.DIRECT2);

    public QAcademy(String variable) {
        super(Academy.class, forVariable(variable));
    }

    public QAcademy(Path<? extends Academy> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAcademy(PathMetadata metadata) {
        super(Academy.class, metadata);
    }

}

