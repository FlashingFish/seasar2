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
package org.seasar.extension.jdbc.gen.model;

import org.seasar.extension.jdbc.EntityMeta;
import org.seasar.extension.jdbc.PropertyMeta;
import org.seasar.extension.jdbc.gen.ConditionAttributeModel;
import org.seasar.extension.jdbc.gen.ConditionAttributeModelFactory;
import org.seasar.extension.jdbc.gen.ConditionMethodModel;
import org.seasar.extension.jdbc.gen.ConditionMethodModelFactory;
import org.seasar.extension.jdbc.gen.ConditionModel;
import org.seasar.extension.jdbc.gen.ConditionModelFactory;
import org.seasar.extension.jdbc.where.ComplexWhere;
import org.seasar.extension.jdbc.where.condition.AbstractEntityCondition;
import org.seasar.framework.util.ClassUtil;

/**
 * {@link ConditionModelFactory}の実装クラスです。
 * 
 * @author taedium
 */
public class ConditionModelFactoryImpl implements ConditionModelFactory {

    protected ConditionAttributeModelFactory conditionAttributeModelFactory;

    protected ConditionMethodModelFactory conditionMethodModelFactory;

    /**
     * @param conditionAttributeModelFactory
     * @param conditionMethodModelFactory
     */
    public ConditionModelFactoryImpl(
            ConditionAttributeModelFactory conditionAttributeModelFactory,
            ConditionMethodModelFactory conditionMethodModelFactory) {
        super();
        this.conditionAttributeModelFactory = conditionAttributeModelFactory;
        this.conditionMethodModelFactory = conditionMethodModelFactory;
    }

    public ConditionModel getConditionModel(EntityMeta entityMeta,
            String className) {
        ConditionModel conditionModel = new ConditionModel();
        conditionModel.setClassName(className);
        String[] elements = ClassUtil.splitPackageAndShortClassName(className);
        conditionModel.setPackageName(elements[0]);
        conditionModel.setShortClassName(elements[1]);
        conditionModel.setEntityMeta(entityMeta);
        for (int i = 0; i < entityMeta.getPropertyMetaSize(); i++) {
            PropertyMeta propertyMeta = entityMeta.getPropertyMeta(i);
            if (propertyMeta.isTransient()) {
                continue;
            }
            if (propertyMeta.isRelationship()) {
                doConditionMethodModel(conditionModel, propertyMeta);
            } else {
                doConditionAttributeModel(conditionModel, propertyMeta);
            }
        }
        doImportPackageNames(conditionModel, entityMeta);
        return conditionModel;
    }

    protected void doConditionAttributeModel(ConditionModel conditionModel,
            PropertyMeta propertyMeta) {
        ConditionAttributeModel attributeModel = conditionAttributeModelFactory
                .getConditionAttributeModel(propertyMeta);
        conditionModel.addConditionAttributeModel(attributeModel);
    }

    protected void doConditionMethodModel(ConditionModel conditionModel,
            PropertyMeta propertyMeta) {
        ConditionMethodModel methodModel = conditionMethodModelFactory
                .getConditionMethodModel(propertyMeta);
        conditionModel.addConditionMethodModel(methodModel);
    }

    /**
     * インポートするパッケージ名を処理します。
     * 
     * @param model
     *            エンティティ条件クラスのモデル
     * @param entityMeta
     *            エンティティ
     */
    protected void doImportPackageNames(ConditionModel conditionModel,
            EntityMeta entityMeta) {
        addImportPackageName(conditionModel, ComplexWhere.class);
        addImportPackageName(conditionModel, AbstractEntityCondition.class);
        for (ConditionAttributeModel attributeModel : conditionModel
                .getConditionAttributeModelList()) {
            addImportPackageName(conditionModel, attributeModel
                    .getAttributeClass());
            addImportPackageName(conditionModel, attributeModel
                    .getConditionClass());
        }
    }

    protected void addImportPackageName(ConditionModel conditionModel,
            Class<?> clazz) {
        String pakcageName = ClassUtil.getPackageName(clazz);
        if (pakcageName != null && !"java.lang".equals(pakcageName)) {
            conditionModel.addImportPackageName(clazz.getName());
        }
    }

}