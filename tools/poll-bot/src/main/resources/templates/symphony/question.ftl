<messageML>
  <card iconSrc="data:image/svg+xml,%0A%3Csvg width='28px' height='28px' fill='%23000000' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' version='1.1' x='0px' y='0px' viewBox='0 0 100 100' enable-background='new 0 0 20 20' xml:space='preserve'%3E%3Cpath d='M87.2,54.039c-0.722,0-1.406,0.148-2.038,0.397c-3.721-9.993-12.229-17.654-22.741-20.203V21.047 c1.607-0.392,2.989-1.565,3.551-3.251c0.867-2.604-0.543-5.416-3.149-6.285c-2.604-0.867-5.418,0.545-6.285,3.151 c-0.865,2.603,0.545,5.419,3.15,6.285c0.133,0.043,0.267,0.057,0.4,0.089v12.725c-1.734-0.287-3.504-0.473-5.32-0.473h-9.515 c-1.741,0-3.438,0.176-5.105,0.44V21.047c1.608-0.392,2.988-1.565,3.551-3.251c0.867-2.604-0.543-5.416-3.149-6.285 c-2.604-0.867-5.419,0.545-6.286,3.151c-0.865,2.603,0.545,5.419,3.15,6.285c0.133,0.043,0.267,0.057,0.401,0.089v13.143 c-10.614,2.495-19.213,10.194-22.96,20.257c-0.631-0.249-1.314-0.396-2.035-0.396c-3.064,0-5.548,2.483-5.548,5.547v8.946 c0,3.063,2.484,5.547,5.547,5.547v12.615H87.2V74.079c3.063,0,5.546-2.483,5.546-5.547v-8.946 C92.746,56.522,90.264,54.039,87.2,54.039z M33.577,69.465c-2.983,0-5.405-2.42-5.405-5.404c0-2.987,2.422-5.406,5.405-5.406 c2.985,0,5.405,2.419,5.405,5.406C38.982,67.045,36.562,69.465,33.577,69.465z M66.661,69.465c-2.987,0-5.406-2.42-5.406-5.404 c0-2.987,2.419-5.406,5.406-5.406c2.983,0,5.4,2.419,5.4,5.406C72.062,67.045,69.645,69.465,66.661,69.465z'%3E%3C/path%3E%3C/svg%3E" 
    accent="tempo-bg-color--blue">
    <header>
      <div style='padding-top:1px;padding-left:5px;'>
        <h3>${entity.form.question}</h3>
        <p><span class="tempo-text-color--secondary"> by <mention uid="${entity.form.poller.id[0].value}" />, poll id <hash tag="${entity.form.id.value!''}" /></span></p>
        <br />
      </div>  
      
      <form 
       id="example.symphony.demoworkflow.Question">
        <p><#list entity.buttons.contents as button>
          <button 
               name="${button.name}"
               type="${button.buttonType?lower_case}">
            ${button.text}
          </button>
        </#list></p>
      </form>

    </header> 
    <body>
      <#if entity.header.tags?size != 0>
        <ul>
          <#list entity.header.tags as tag>
            <li><hash tag="${tag.value}" /></li>
          </#list>
        </ul>
      </#if>
    </body>
  </card>
</messageML>