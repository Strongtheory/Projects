#ifndef RANDOMEVENT_H
#define RANDOMEVENT_H

#include <QObject>

#include <QSharedDataPointer>

class RandomEventData;

class RandomEvent : public QObject
{
public:
    enum EventType {
        Unknown = -1,
        None,
        Police,
        Pirate,
        Trader
    };

    RandomEvent();
    RandomEvent(const RandomEvent &);
    RandomEvent &operator=(const RandomEvent &);
    ~RandomEvent();

private:
    QSharedDataPointer<RandomEventData> data;
};

#endif // RANDOMEVENT_H
