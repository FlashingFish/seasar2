/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.extension.jdbc.gen.factory;

import org.seasar.extension.jdbc.gen.EntityConditionBaseModelFactory;
import org.seasar.extension.jdbc.gen.model.AttributeDesc;
import org.seasar.extension.jdbc.gen.model.EntityConditionBaseModel;
import org.seasar.extension.jdbc.gen.model.EntityDesc;
import org.seasar.extension.jdbc.where.ComplexWhere;
import org.seasar.extension.jdbc.where.condition.AbstractEntityCondition;
import org.seasar.extension.jdbc.where.condition.NotNullableCondition;
import org.seasar.extension.jdbc.where.condition.NotNullableStringCondition;
import org.seasar.extension.jdbc.where.condition.NullableCondition;
import org.seasar.extension.jdbc.where.condition.NullableStringCondition;
import org.seasar.framework.util.ClassUtil;

/**
 * {@link EntityConditionBaseModelFactory}の実装クラスです。
 * 
 * @author taedium
 */
public class EntityConditionBaseModelFactoryImpl implements
        EntityConditionBaseModelFactory {

    public EntityConditionBaseModel getEntityConditionBaseModel(
            EntityDesc entityDesc, String className) {
        EntityConditionBaseModel model = new EntityConditionBaseModel();
        model.setClassName(className);
        String[] elements = ClassUtil.splitPackageAndShortClassName(className);
        model.setPackageName(elements[0]);
        model.setShortClassName(elements[1]);
        model.setBaseClassName(AbstractEntityCondition.class.getName());
        model.setShortBaseClassName(AbstractEntityCondition.class
                .getSimpleName());
        model.setEntityDesc(entityDesc);
        doImportPackageNames(model, entityDesc);
        return model;
    }

    /**
     * インポートするパッケージ名を処理します。
     * 
     * @param model
     *            エンティティ基底クラスのモデル
     * @param entityDesc
     *            エンティティ記述
     */
    protected void doImportPackageNames(EntityConditionBaseModel model,
            EntityDesc entityDesc) {
        model.addImportPackageName(ComplexWhere.class.getName());
        model.addImportPackageName(AbstractEntityCondition.class.getName());
        for (AttributeDesc attr : entityDesc.getAttributeDescList()) {
            Class<?> conditionClass = null;
            if (attr.isNullable()) {
                if (attr.getAttributeClass() == String.class) {
                    conditionClass = NullableStringCondition.class;
                } else {
                    conditionClass = NullableCondition.class;
                }
            } else {
                if (attr.getAttributeClass() == String.class) {
                    conditionClass = NotNullableStringCondition.class;
                } else {
                    conditionClass = NotNullableCondition.class;
                }
            }
            model.addImportPackageName(conditionClass.getName());
            String name = ClassUtil.getPackageName(attr.getAttributeClass());
            if (name != null && !"java.lang".equals(name)) {
                model.addImportPackageName(attr.getAttributeClass().getName());
            }
        }
    }
}