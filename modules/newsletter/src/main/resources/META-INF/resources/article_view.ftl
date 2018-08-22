<#--
Web content templates are used to lay out the fields defined in a web
content structure.

Please use the left panel to quickly add commonly used variables.
Autocomplete is also available and can be invoked by typing "${".
-->
Issue: #${IssueNumber.getData()}, <#assign IssueDate_Data = getterUtil.getString(IssueDate.getData())> 
<#if validator.isNotNull(IssueDate_Data)> 
<#assign IssueDate_DateObj = dateUtil.parseDate("yyyy-MM-dd", IssueDate_Data, locale)> ${dateUtil.getDate(IssueDate_DateObj, "MMMM dd, yyyy", locale)} 
</#if>

<#assign 
    serviceContext = staticUtil["com.liferay.portal.kernel.service.ServiceContextThreadLocal"].getServiceContext()
    themeDisplay = serviceContext.getThemeDisplay()
    group_id = themeDisplay.getScopeGroupId()  
    
    JournalArticleLocalService = serviceLocator.findService("com.liferay.journal.service.JournalArticleLocalService")  
>



<#-- Title -->
<h1>${.vars['reserved-article-title'].data}</h1>

<#-- Byline -->
<#assign byline = []>
<#if Articles.getSiblings()?has_content> 
    <#list Articles.getSiblings() as curArticle>  
    <#assign byline = byline + [.vars['reserved-article-author-name'].data]>
    </#list> 
</#if>
<h6>${byline?join(", ")}</h6>
${IssueDescription.getData()} 

<#-- Articles -->
<#-- TODO: Get article order value -->
<p> 
    <#if Articles.getSiblings()?has_content> 
        <#list Articles.getSiblings() as curArticle>  

            <#assign 
                curArticle_map = curArticle.getData()?eval
                curArticle_classPK = curArticle_map.classPK
                article = JournalArticleLocalService.getLatestArticle(curArticle_classPK?number)
                article_id = article.articleId
                article_content = JournalArticleLocalService.getArticleContent(group_id, article_id, null, locale, themeDisplay)
                >

                ${article_content}
                
            
        </#list> 
    </#if>
</p>