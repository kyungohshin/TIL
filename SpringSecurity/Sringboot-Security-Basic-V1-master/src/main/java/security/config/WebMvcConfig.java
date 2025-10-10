//package security.config;
//
//import org.springframework.boot.web.servlet.view.MustacheViewResolver;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer{
//
//
//// 머스테치의 설정을 변경. xxx.mustache 이렇게되어야 인식할텐데 .html도 인식하도록 변경
//  @Override
//  public void configureViewResolvers(ViewResolverRegistry registry) {
//      MustacheViewResolver resolver = new MustacheViewResolver();
//
//      resolver.setCharset("UTF-8");
//      resolver.setContentType("text/html;charset=UTF-8");
//      resolver.setPrefix("classpath:/templates/");
//      resolver.setSuffix(".html");
//
//      registry.viewResolver(resolver);
//  }
//}
