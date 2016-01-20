#ifndef WARE_H
#define WARE_H

#include <QObject>

#include <QExplicitlySharedDataPointer>
#include <QDataStream>

class WareData;

class Ware : public QObject
{
public:
    enum Good {
        Unknown = -1,
        Water,
        Furs,
        Food,
        Ore,
        Games,
        Firearms,
        Medicines,
        Machines,
        Narcotics,
        Robots,

        SIZE_GOOD // DO NOT MOVE OR REMOVE
    };

    Ware(Good good = Unknown);
    Ware(const Ware &);
    Ware &operator=(const Ware &);
    ~Ware();

    Good getGood() const;
    QString getName() const;
    int getMinimumTechLevelToProduce() const;
    int getMinimumTechLevelToUse() const;
    int getTechLevelProduction() const;
    int getBasePrice() const;
    int getPriceIncreasePerTechLevel() const;
    int getVariance() const;
    int getMaxPriceInSpaceTrade() const;
    int getMinPriceInSpaceTrade() const;
private:
    QExplicitlySharedDataPointer<WareData> data;
};

Q_DECLARE_TYPEINFO(Ware, Q_MOVABLE_TYPE);

bool operator <(Ware a, Ware b);

QDataStream& operator<<(QDataStream& stream, const Ware ware);
QDataStream& operator>>(QDataStream& stream, Ware& ware);

#endif // WARE_H
