package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.ProductModel;
import com.fernandocejas.android10.sample.presentation.view.activity.ProductActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductFragment extends BaseFragment {

    @BindView(R.id.add_to_wishlist)
    Button addToWishlist;
    @BindView(R.id.add_to_cart)
    Button addToCart;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.product_image)
    ImageView productImage;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.price_button)
    Button priceButton;
    @BindView(R.id.description)
    TextView description;

    private int productId;

    public static ProductFragment newInstance(int productId) {
        ProductFragment productFragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ProductActivity.PRODUCT_ID, productId);
        productFragment.setArguments(bundle);
        return productFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);

        productId = getArguments().getInt(ProductActivity.PRODUCT_ID, Integer.MIN_VALUE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        //// TODO: 02/05/2017 демо значения
        ProductModel productModel = new ProductModel();
        productModel.setTitle("Сифон O!range, цвет: матовый оранжевый, 1 л");
        productModel.setCurrency("Р");
        productModel.setDescription("От производителя\n" +
                "Cифон O!range предназначен для приготовления газированной воды, напитков и коктейлей. С помощью сифона для газирования воды O!range можно быстро и легко готовить натуральные и очень вкусные напитки и коктейли. Стильный дизайн и актуальные цвета делают сифоны O!range незаменимыми на любой кухне - в квартире и в загородном доме, на даче и в офисе. \n" +
                "\n" +
                "ПЕЙТЕ натуральные напитки\n" +
                "Готовьте натуральную газировку без красителей и консервантов. Используйте ключевую воду, домашнее варенье, компоты, фрукты и другие натуральные продукты. Не бойтесь экспериментировать. \n" +
                "\n" +
                "ЭКОНОМЬТЕ свои деньги\n" +
                "Вам больше не нужно покупать газированную воду и напитки в магазине. Для приготовления газированной воды вам потребуется сифон O!range и вода из под крана, очищенная бытовым фильтром. \n" +
                "\n" +
                "ИСПОЛЬЗУЙТЕ дома и на даче\n" +
                "Сифон для газирования воды O!range прост и удобен в применении. Вы всегда сможете приготовить освежающий напиток за несколько секунд. \n" +
                "\n" +
                "ЗАБОТЬТЕСЬ о природе\n" +
                "Используя сифон O!range, вы избегаете покупок газированной воды и напитков в пластиковой упаковке. Чем меньше пластика мы используем, тем меньше мы загрязняем окружающую среду. \n" +
                "\n" +
                "ДАРИТЕ близким\n" +
                "Сифон для газирования воды O!range - это стильный подарок в дизайнерской упаковке. Он создает праздник и отличное настроение. Порадуйте своих близким незаурядным подарком. \n" +
                "\n" +
                "ВНИМАНИЕ: \n" +
                "C помощью сифона O!range можно газировать только чистую воду. Напиток или коктейль необходимо смешивать в отдельной емкости, так как емкость сифона предназначена только для газирования воды. \n" +
                "\n" +
                "ЧТО НОВОГО: \n" +
                "Толстые стенки алюминиевого сосуда, широкий удобный рычаг, улучшенный пластик, прокладки и клапан, новые цвета, противоскользящее покрытие, удобная инструкция в картинках, подарочная дизайнерская упаковка. \n" +
                "\n" +
                "Рецепт домашней газировки. Напиток готовится 30 секунд! \n" +
                "Газируйте воду с помощью сифона.\n" +
                "Наполните бокал газированной водой из сифона.\n" +
                "Добавьте в бокал 1-2 столовых ложки любого кисло-сладкого домашнего варенья. Идеально подходит варенье из желтой алычи.\n" +
                "Тщательно размешайте содержимое бокала.\n" +
                "Добавьте в бокал кусочки льда и дольку апельсина.\n" +
                "Наслаждайтесь вкусом натурального домашнего лимонада! \n" +
                "\n" +
                "Сифон для газирования воды O!range предназначен для покупателей, которые: \n" +
                "Заботятся о своем здоровье.\n" +
                "Любят вкус газированной воды.\n" +
                "Экспериментируют с приготовлением собственных натуральных напитков.\n" +
                "Готовят различные коктейли.\n" +
                "Хотят подарить родным или близким незаурядный и полезный подарок. \n" +
                "\n" +
                "Преимущества сифона для газирования воды O!range: \n" +
                "Готовит только натуральную газировку.\n" +
                "Низкая себестоимость приготовления напитков.\n" +
                "Простота и удобство использования.\n" +
                "Надежный алюминиевый корпус.\n" +
                "Прошел гигиеническую экспертизу. \n" +
                "\n" +
                "Комплектация сифона O!Range:\n" +
                "Сифон бытовой для газирования воды.\n" +
                "Патрон для пищевого баллончика.\n" +
                "Аксессуар для удобного извлечения уплотнителя.\n" +
                "Инструкция, гарантийный талон Характеристики:\n" +
                "\n" +
                "Материал: алюминий.\n" +
                "Объем: 1 л.\n" +
                "Цвет: оранжевый матовый.\n" +
                "Размер: 10,7 см х 10,7 см х 34 см.\n" +
                "Покрытие: противоскользящее.\n" +
                "Разработано: LC Group, Россия.\n" +
                "Произведено в КНР под контролем LC Group. \n" +
                "\n" +
                "УВАЖАЕМЫЕ КЛИЕНТЫ! ВНИМАНИЕ! \n" +
                "Для приготовление 1 литра газированной воды необходим 1 пищевой баллончик с газом СО2 весом 8 грамм, рекомендованный для бытовых сифонов. Баллончики приобретаются отдельно от сифона. \n" +
                "\n" +
                "ВНИМАНИЕ! \n" +
                "Перед применением сифона необходимо ознакомится с инструкцией пользователя. \n" +
                "\n" +
                "Товар сертифицирован");
        productModel.setId(1);
        productModel.setInStock(true);
        productModel.setPhotos("https://ozon-st.cdn.ngenix.net/multimedia/audio_cd_covers/1010913789.jpg");
        productModel.setPrice(2249);
        setProductInfo(productModel);
        //// TODO: 02/05/2017 демо значения
        return view;
    }

    private void setProductInfo(ProductModel productInfo) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(productInfo.getTitle());
        Glide.with(this).load(productInfo.getPhotos()).into(productImage);
        title.setText(productInfo.getTitle());
        priceButton.setText(String.format("%s%s", productInfo.getPrice(), productInfo.getCurrency()));
        description.setText(productInfo.getDescription());
    }

}
