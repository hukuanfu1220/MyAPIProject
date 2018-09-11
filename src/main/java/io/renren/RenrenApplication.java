package io.renren;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import io.renren.datasources.DynamicDataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@Import({DynamicDataSourceConfig.class})
public class RenrenApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RenrenApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		//AVOSCloud.initialize("IJ9yXePUxhvz9LGRr8UwufdI-gzGzoHsz","WvqLAFMXcNeGlAKQQ1dyaBWY","dKAYBhoNiax3Rq78jsM2YT89");
		AVOSCloud.initialize("IJ9yXePUxhvz9LGRr8UwufdI-gzGzoHsz","WvqLAFMXcNeGlAKQQ1dyaBWY","dKAYBhoNiax3Rq78jsM2YT89");
		//AVOSCloud.useAVCloudCN();
		AVOSCloud.setDebugLogEnabled(true);


		return application.sources(RenrenApplication.class);
	}
}
