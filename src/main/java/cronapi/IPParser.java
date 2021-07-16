package cronapi;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.model.*;
import com.maxmind.geoip2.record.*;

import cronapi.CronapiMetaData;
import cronapi.ParamMetaData;


/**
 * Descrição da Função ...
 *
 * @author Lincoln Roberto Martins Minto
 * @version 1.0
 * @since 2021-07-16
 *
 */

@CronapiMetaData(categoryName = "MEUIP")
public class IPParser {

	@CronapiMetaData(type = "function", name = "getIPDetais", description = "Descrição da Função")
	public static Map<String, Object> getIPDetais(@ParamMetaData(description = "ID da Conta: ID da da conta") int accountId, @ParamMetaData(description = "Licença: Licença da conta") String licenseKey, @ParamMetaData(description = "IP: Ip do usuário") String ip) throws Exception {
		
		try (WebServiceClient client = new WebServiceClient.Builder(accountId, licenseKey).host("geolite.info").build()) {
			InetAddress ipAddress = InetAddress.getByName(ip);

			// Do the lookup
			CityResponse response = client.city(ipAddress);

			Country country = response.getCountry();
			System.out.println(country.getIsoCode());            // 'US'
			System.out.println(country.getName());               // 'United States'
			System.out.println(country.getNames().get("zh-CN")); // '美国'

			Subdivision subdivision = response.getMostSpecificSubdivision();
			System.out.println(subdivision.getName());       // 'Minnesota'
			System.out.println(subdivision.getIsoCode());    // 'MN'

			City city = response.getCity();
			System.out.println(city.getName());       // 'Minneapolis'

			Postal postal = response.getPostal();
			System.out.println(postal.getCode());       // '55455'

			Location location = response.getLocation();
			System.out.println(location.getLatitude());        // 44.9733
			System.out.println(location.getLongitude());       // -93.2323

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("country", country);
			map.put("subdivision", subdivision);
			map.put("city", city);
			map.put("postal", postal);
			map.put("location", location);

			return map;
		}
		
	}

}