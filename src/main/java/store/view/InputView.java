package store.view;

import static camp.nextstep.edu.missionutils.Console.readLine;

import camp.nextstep.edu.missionutils.Console;
import store.model.Select;

public class InputView {

    public String readOrderItems() {
        System.out.println("\n구매하실 상품과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return Console.readLine();
    }

    public String readPromotionBenefitAcceptance(String productName, int count) {
        System.out.printf("\n현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n", productName, count);
        return Console.readLine();
    }

    public String readFullPriceAcceptance(String productName, int count) {
        System.out.printf("\n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n", productName, count);
        return Console.readLine();
    }

    public String readMembershipAcceptance() {
        System.out.println("\n멤버십 할인을 받으시겠습니까? (Y/N)");
        return Console.readLine();
    }

    public String readContinueOrder() {
        System.out.println("\n감사합니다. 구매하고 싶은 다른 사품이 있나요? (Y/N)");
        return Console.readLine();
    }
}
