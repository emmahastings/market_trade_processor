package com.hastings.market_trade_processor.repository;

import com.hastings.market_trade_processor.model.entity.TradeMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by emmakhastings on 08/12/2018
 *
 * @author emmakhastings
 * <p>
 * Repository class to allow query access to underlying datastore
 */
@Repository
public interface TradeMessageRepository extends JpaRepository<TradeMessage, Long> {
    Long countByUserId(String userId);
}
