/*
 * SURITTEC
 * Copyright 2015, TTUS TECNOLOGIA DA INFORMACAO LTDA, 
 * and individual contributors as indicated by the @authors tag
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package br.com.surittec.surijpa.criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

/**
 * Suporte para constru��o de queries em JPQL.
 */
public class JPQL {

	private EntityManager entityManager;

	private List<String> select;
	private List<String> from;
	private List<String> where;
	private Map<String, Object> params;
	private List<String> group;
	private List<String> having;
	private List<String> order;

	private Integer firstResult;
	private Integer maxResults;

	// ----------------------------------------------------------------------------
	// CONSTRUCTOR
	// ----------------------------------------------------------------------------

	public JPQL(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.select = new ArrayList<String>();
		this.from = new ArrayList<String>();
		this.where = new ArrayList<String>();
		this.params = new HashMap<String, Object>();
		this.group = new ArrayList<String>();
		this.having = new ArrayList<String>();
		this.order = new ArrayList<String>();
	}

	// ----------------------------------------------------------------------------
	// PUBLIC
	// ----------------------------------------------------------------------------

	/**
	 * Inclui cl�usulas SELECT para as queries.
	 * 
	 * @param select
	 * @return
	 */
	public JPQL select(String... select) {
		return select(Arrays.asList(select));
	}

	/**
	 * Inclui cl�usulas SELECT para as queries.
	 * 
	 * @param select
	 * @return
	 */
	public JPQL select(Collection<String> selects) {
		this.select.addAll(selects);
		return this;
	}

	/**
	 * Inclui cl�usulas FROM para as queries.
	 * 
	 * @param from
	 * @return
	 */
	public JPQL from(String... from) {
		return from(Arrays.asList(from));
	}

	/**
	 * Inclui cl�usulas FROM para as queries.
	 * 
	 * @param from
	 * @return
	 */
	public JPQL from(Collection<String> froms) {
		this.from.addAll(froms);
		return this;
	}

	/**
	 * Alias para o m�todo from()
	 * 
	 * @param joins
	 * @return
	 */
	public JPQL join(String... joins) {
		return from(joins);
	}

	/**
	 * Alias para o m�todo from()
	 * 
	 * @param joins
	 * @return
	 */
	public JPQL join(Collection<String> joins) {
		return from(joins);
	}

	/**
	 * Alias para o m�todo from()
	 * 
	 * @param joins
	 * @return
	 */
	public JPQL innerJoin(String... joins) {
		return join(true, false, Arrays.asList(joins));
	}

	/**
	 * Alias para o m�todo from()
	 * 
	 * @param joins
	 * @return
	 */
	public JPQL innerJoin(Collection<String> joins) {
		return join(true, false, joins);
	}

	/**
	 * Alias para o m�todo from()
	 * 
	 * @param joins
	 * @return
	 */
	public JPQL innerJoinFetch(String... joins) {
		return join(true, true, Arrays.asList(joins));
	}

	/**
	 * Alias para o m�todo from()
	 * 
	 * @param joins
	 * @return
	 */
	public JPQL innerJoinFetch(Collection<String> joins) {
		return join(true, true, joins);
	}
	
	/**
	 * Alias para o m�todo from()
	 * 
	 * @param joins
	 * @return
	 */
	public JPQL innerJoin(boolean fetch, String... joins) {
		return join(true, fetch, Arrays.asList(joins));
	}

	/**
	 * Alias para o m�todo from()
	 * 
	 * @param joins
	 * @return
	 */
	public JPQL innerJoin(boolean fetch, Collection<String> joins) {
		return join(true, fetch, joins);
	}

	/**
	 * Alias para o m�todo from()
	 * 
	 * @param joins
	 * @return
	 */
	public JPQL leftJoin(String... joins) {
		return join(false, false, Arrays.asList(joins));
	}

	/**
	 * Alias para o m�todo from()
	 * 
	 * @param joins
	 * @return
	 */
	public JPQL leftJoin(Collection<String> joins) {
		return join(false, false, joins);
	}

	/**
	 * Alias para o m�todo from()
	 * 
	 * @param joins
	 * @return
	 */
	public JPQL leftJoinFetch(String... joins) {
		return join(false, true, Arrays.asList(joins));
	}

	/**
	 * Alias para o m�todo from()
	 * 
	 * @param joins
	 * @return
	 */
	public JPQL leftJoinFetch(Collection<String> joins) {
		return join(false, true, joins);
	}
	
	/**
	 * Alias para o m�todo from()
	 * 
	 * @param joins
	 * @return
	 */
	public JPQL leftJoin(boolean fetch, String... joins) {
		return join(false, fetch, Arrays.asList(joins));
	}

	/**
	 * Alias para o m�todo from()
	 * 
	 * @param joins
	 * @return
	 */
	public JPQL leftJoin(boolean fetch, Collection<String> joins) {
		return join(false, fetch, joins);
	}

	/**
	 * Alias para o m�todo and()
	 * 
	 * @param where
	 * @return
	 */
	public JPQL where(String... condition) {
		return and(condition);
	}

	/**
	 * Alias para o m�todo and()
	 * 
	 * @param where
	 * @return
	 */
	public JPQL where(Collection<String> conditions) {
		return and(conditions);
	}

	/**
	 * Inclui cl�usulas AND no WHERE para as queries.
	 * 
	 * @param where
	 * @return
	 */
	public JPQL and(String... condition) {
		return and(Arrays.asList(condition));
	}

	/**
	 * Inclui cl�usulas AND no WHERE para as queries.
	 * 
	 * @param where
	 * @return
	 */
	public JPQL and(Collection<String> conditions) {
		this.where.addAll(conditions);
		return this;
	}

	/**
	 * Inclui cl�usulas OR no WHERE para as queries
	 * 
	 * @param where
	 * @return
	 */
	public JPQL or(String... condition) {
		return or(Arrays.asList(condition));
	}

	/**
	 * Inclui cl�usulas OR no WHERE para as queries
	 * 
	 * @param where
	 * @return
	 */
	public JPQL or(Collection<String> conditions) {
		return and(String.format("(%s)", StringUtils.join(conditions, " OR ")));
	}

	/**
	 * Inclui valores para os par�metros nominais das cl�usulas.
	 * 
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public JPQL withParam(String paramName, Object paramValue) {
		this.params.put(paramName, paramValue);
		return this;
	}

	/**
	 * Inclui cl�usulas GROUP BY para as queries.
	 * 
	 * @param group
	 * @return
	 */
	public JPQL groupBy(String... group) {
		return groupBy(Arrays.asList(group));
	}

	/**
	 * Inclui cl�usulas GROUP BY para as queries.
	 * 
	 * @param group
	 * @return
	 */
	public JPQL groupBy(Collection<String> groups) {
		this.group.addAll(groups);
		return this;
	}
	
	/**
	 * Inclui cl�usulas HAVING para as queries.
	 * 
	 * @param having
	 * @return
	 */
	public JPQL having(String... having) {
		return having(Arrays.asList(having));
	}
	
	/**
	 * Inclui cl�usulas HAVING para as queries.
	 * 
	 * @param group
	 * @return
	 */
	public JPQL having(Collection<String> havings) {
		this.having.addAll(havings);
		return this;
	}

	/**
	 * Inclui cl�usulas ORDER BY para as queries.
	 * 
	 * @param order
	 * @return
	 */
	public JPQL orderBy(String... order) {
		return orderBy(Arrays.asList(order));
	}

	/**
	 * Inclui cl�usulas ORDER BY para as queries.
	 * 
	 * @param order
	 * @return
	 */
	public JPQL orderBy(Collection<String> orders) {
		this.order.addAll(orders);
		return this;
	}

	/**
	 * Informa qual ser� o �ndice do primeiro resultado retornado pela query.
	 * 
	 * @param firstResult
	 * @return
	 */
	public JPQL firstResult(Integer firstResult) {
		this.firstResult = firstResult;
		return this;
	}

	/**
	 * Limita a quantidade de resultados trazidos pela query.
	 * 
	 * @param firstResult
	 * @return
	 */
	public JPQL maxResults(Integer maxResults) {
		this.maxResults = maxResults;
		return this;
	}

	/**
	 * Retorna uma lista de entidades que atendem aos crit�rios da busca. J� faz
	 * o <code>cast</code> para a classe <code>resultType</code> passada.
	 * 
	 * @param resultType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList(Class<T> resultType) {
		return getQuery().getResultList();
	}

	/**
	 * Retorna uma lista de entidades que atendem aos crit�rios da busca.
	 * 
	 * @return
	 */
	public List<?> getResultList() {
		return getQuery().getResultList();
	}

	/**
	 * Retorna uma �nica entidade que atenda aos crit�rios da busca. J� faz o
	 * <code>cast</code> para a classe <code>resultType</code> passada.
	 * 
	 * @param resultType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getSingleResult(Class<T> resultType) {
		return (T) getSingleResult();
	}

	/**
	 * Retorna uma �nica entidade que atenda aos crit�rios da busca.
	 * 
	 * @return
	 */
	public Object getSingleResult() {
		return getQuery().getSingleResult();
	}

	/**
	 * Retorna uma �nica entidade que atenda aos crit�rios da busca. Trata o
	 * caso de n�o haver resultado, retornando <code>null</code>. J� faz o
	 * <code>cast</code> para a classe <code>resultType</code> passada.
	 * 
	 * @param resultType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getAnyResult(Class<T> resultType) {
		return (T) getAnyResult();
	}

	/**
	 * Retorna uma �nica entidade que atenda aos crit�rios da busca. Trata o
	 * caso de n�o haver resultado, retornando <code>null</code>.
	 * 
	 * @return
	 */
	public Object getAnyResult() {
		List<?> result = getResultList();
		if (result != null && !result.isEmpty())
			return result.get(0);
		return null;
	}

	@Override
	public String toString() {
		StringBuilder query = new StringBuilder();

		if (!select.isEmpty())
			append(query, "select", select, ",");
		append(query, "from", from);
		if (!where.isEmpty())
			append(query, "where", where, "and");
		if (!group.isEmpty())
			append(query, "group by", group, ",");
		if (!having.isEmpty())
			append(query, "having", having, ",");
		if (!order.isEmpty())
			append(query, "order by", order, ",");

		return query.toString();
	}

	// ----------------------------------------------------------------------------
	// PRIVATE
	// ----------------------------------------------------------------------------

	private JPQL join(boolean inner, boolean fetch, Collection<String> joins) {
		List<String> list = new ArrayList<String>();
		for (String join : joins) {
			list.add(String.format("%s join %s %s", inner ? "inner" : "left", fetch ? "fetch" : "", join));
		}
		return from(list);
	}
	
	private Query getQuery() {
		Query query = entityManager.createQuery(this.toString());

		for (String paramName : params.keySet()) {
			query.setParameter(paramName, params.get(paramName));
		}

		if (firstResult != null)
			query.setFirstResult(firstResult);
		if (maxResults != null)
			query.setMaxResults(maxResults);

		return query;
	}

	private void append(StringBuilder sb, String part, List<?> values, String separator) {
		append(sb, part);
		for (int i = 0; i < values.size(); i++) {
			append(sb, values.get(i));
			if (i != (values.size() - 1))
				append(sb, separator);
			sb.append(" ");
		}
	}

	private void append(StringBuilder sb, String part, List<?> values) {
		append(sb, part);
		for (Object value : values)
			append(sb, value);
	}

	private void append(StringBuilder sb, Object value) {
		sb.append(value);
		sb.append(" ");
	}

}
